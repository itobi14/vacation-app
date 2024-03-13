package app.rest;

import app.APIConfig;
import app.exceptions.NotAcceptableException;
import app.models.Account;
import app.repositories.AccountJPARepository;
import app.security.JWToken;
import app.serialization.ViewClasses;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    APIConfig apiConfig;

    @Autowired
    private AccountJPARepository accountsRepo;

    @JsonView(ViewClasses.Summary.class)
    @PostMapping(path = "/login")
    public ResponseEntity<Account> authenticateAccount(
            @RequestBody ObjectNode signInInfo,
            HttpServletRequest request) {

        String email = signInInfo.get("email").asText();
        String password = signInInfo.get("password").asText();

        // check whether we need and have the source IP-address of the user
        String ipAddress = JWToken.getIpAddress(request);
        if (ipAddress == null) {
            throw new NotAcceptableException("Cannot identify your source IP-Address.");
        }

        List<Account> accounts = accountsRepo.findByQuery("Accounts_find_by_email", email);
        Account account = !accounts.isEmpty() ? accounts.get(0) : null;
        System.out.println(accounts);

        if (account == null || !account.verifyPassword(password)) {
            throw new NotAcceptableException("Cannot authenticate account with email = " + email);
        }

        // Issue a token for the account, valid for some time
        JWToken jwToken = new JWToken(account.getFullName(), account.getId(), account.getRole(), ipAddress);
        String tokenString = jwToken.encode(this.apiConfig.getIssuer(),
                this.apiConfig.getPassphrase(),
                this.apiConfig.getTokenDurationOfValidity());

        return ResponseEntity.accepted()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(account);
    }
}