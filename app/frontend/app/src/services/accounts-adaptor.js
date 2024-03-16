import {Account} from "@/models/Account";
import {Accommodation} from "@/models/Accommodation";

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
        let response = await this.fetchJson(this.RESOURCES_URL,
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

    async findFavorites(accountId) {

        try {
            const favoritesData = await this.fetchJson(this.RESOURCES_URL + "/" + accountId + "/favorites");

            if (!favoritesData) {
                console.error('Invalid response data:', favoritesData);
                return [];
            }

            return favoritesData?.map(Accommodation.copyConstructor);

        } catch (error) {
            console.error('Error fetching favorites data:', error);
            return [];
        }
    }

    async findFavorite(accountId, accommodationId) {

        try {
            let isFavorite = false;
            const favoriteData = await this.fetchJson(this.RESOURCES_URL + "/" + accountId + "/favorites/" + accommodationId);

            if (!favoriteData) {
                return isFavorite;
            }

            isFavorite = true;
            return isFavorite;

        } catch (error) {
            return null;
        }
    }

    async addFavorite(accountId, accommodationId) {

        let response = await this.fetchJson(this.RESOURCES_URL + "/" + accountId + '/favorites/' + accommodationId,
            {
                method: 'POST',
            })

        if (response.ok) {
            return await response.json();
        } else {
            // console.log(response)
            return null;
        }
    }

    async removeFavorite(accountId, accommodationId) {

        let response = await this.fetchJson(this.RESOURCES_URL + "/" + accountId + '/favorites/' + accommodationId,
            {
                method: 'DELETE',
            })
        if (response.ok) {
            return await response.json();
        } else {
            // console.log(response)
            return null;
        }
    }

    async findBookingHistory(id) {

        try {
            const bookingData = await this.fetchJson(this.RESOURCES_URL + "/" + id + "/bookingHistory");

            if (!bookingData) {
                console.error('Invalid response data:', bookingData);
                return [];
            }

            return bookingData?.map(Accommodation.copyConstructor);

        } catch (error) {
            console.error('Error fetching booking data:', error);
            return [];
        }
    }

}