export class SessionService {
    RESOURCES_URL;                  // the back-end base url for authentication resources
    BROWSER_STORAGE_ITEM_NAME;      // the key into browser storage for retaining the token and account
    _currentToken;                  // the current authentication token of this session
                                    // to be injected in the authorization header of every outgoing request
    _currentAccount;                // the account instant of the currently logged on user

    constructor(resourcesUrl, browserStorageItemName) {

        // console.log("Created SessionService...");
        this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
        this.RESOURCES_URL = resourcesUrl;
        this._currentAccount = null;
        this._currentToken = null;
        this.getTokenFromBrowserStorage(); // retrieve the current user info from browser storage, e.g. after a page reload or when a new tab is opened.
    }

    get currentToken() {
        return this._currentToken;
    }

    get currentAccount() {
        return this._currentAccount;
    }

    isAdmin() {
        return this._currentAccount?.role?.toLowerCase().includes("admin");
    }
    isAuthenticated() {
        return this._currentAccount != null;
    }

    getTokenFromBrowserStorage() {

        if (this._currentToken != null) return this._currentToken
        this._currentToken = window.localStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
        let jsonAccount = window.localStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME+"_ACC");

        if (jsonAccount != null) {
            this._currentAccount = JSON.parse(jsonAccount);
        }
        // console.log("SessionService recovered token: ", this._currentToken);
        // console.log("Current Account:", this._currentAccount);
        return this._currentToken;
    }

    saveTokenIntoBrowserStorage(token, account) {

        this._currentToken = token;
        this._currentAccount = account;

        if (token == null) {
            this._currentAccount = null;

            // remove token+account from local storage
            window.localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
            window.localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME+"_ACC");

        } else {
            // console.log("New token for " + account.name + ": " + token);
            window.localStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
            window.localStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME+"_ACC", JSON.stringify(account));
        }
    }

    async asyncSignIn(email, password) {

        const body = JSON.stringify({ email: email, password: password });
        let response = await fetch(this.RESOURCES_URL + "/login",
            {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: body
            })
        if (response.ok) {
            let account = await response.json();
            this.saveTokenIntoBrowserStorage(
                response.headers.get('Authorization'),
                account);
            return account;
        } else {
            console.log(response)
            return null;
        }
    }

    signOut() {
        this.saveTokenIntoBrowserStorage(null, null);
    }
}