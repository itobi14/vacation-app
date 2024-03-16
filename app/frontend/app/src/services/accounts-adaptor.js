import {Account} from "@/models/Account";

export class AccountsAdaptor {

    RESOURCES_URL;

    constructor(resourcesUrl) {
        this.RESOURCES_URL = resourcesUrl;
        // console.log("Created AccountsAdaptor for " + resourcesUrl);
    }

    // issues an asynchronous AJAX fetch request with JSON content
    // request methods PUT, POST and DELETE and request headers can be passed via options
    async fetchJson(url, options = null) {

        let response = await fetch(url, options)
        if (response.ok) {
            return await response.json();
        } else {
            // the response body provides the http-error information
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
    }

    async createAccount(firstName, lastName, email, password) {

        const body = JSON.stringify({ firstName: firstName, lastName: lastName, email: email, password: password });
        let response = await fetch(this.RESOURCES_URL,
            {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: body
            })
        if (response.ok) {
            return await response.json();
        } else {
            console.log(response)
            return null;
        }
    }

    async findById(id){

        if (id === undefined) {
            console.error("ID is undefined");
            return null;
        }

        const accountsData = await this.fetchJson(this.RESOURCES_URL + "/" + id);
        return Account.copyConstructor(accountsData);
    }

}