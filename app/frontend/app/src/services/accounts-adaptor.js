/* eslint-disable */

import {Account} from "@/models/Account.js";

export class AccountsAdaptor /* implements RESTAdaptor<Account> */ {

    resourcesUrl;   // the URL of the accounts resource endpoint

    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
        console.log("Created AccountsAdaptor for " + resourcesUrl);
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

    async asyncFindAll() /* :Promise<Account[]> */ {

        console.log('AccountsAdaptor.asyncFindAll()...');
        const accountsData = await this.fetchJson(this.resourcesUrl);
        return accountsData?.map(Account.copyConstructor);
    }

    // does not yet implement the query parameters;
    // those will be added later and be merged into the fetch url.
    async asyncFindById(id) /* :Promise<Account> */ {
        console.log('AccountsAdaptor.asyncFindById(' + id + ')...');
        const accountData = await this.fetchJson(this.resourcesUrl + "/" + id);
        return Account.copyConstructor(accountData);
    }

    async asyncSave(account) /* :Promise<Account> */ {
        console.log('TODO AccountsAdaptor.asyncSave(account)...');
        return "TODO AccountsAdaptor.asyncSave";
    }

    async asyncDeleteById(id) /* :Promise<void> */ {
        console.log('AccountsAdaptor.asyncDeleteById(' + id + ')...');
        return this.fetchJson(this.resourcesUrl + "/" + id,
            {
                method: 'DELETE'
            });
    }
}