import {Accommodation} from "@/models/Accommodation";

export class AccommodationsAdaptor {

    resourcesUrl;   // the URL of the accounts resource endpoint

    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
        // console.log("Created AccommodationAdaptor for " + resourcesUrl);
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

    async findAll() /* :Promise<Account[]> */ {

        // console.log('AccommodationAdaptor.asyncFindAll()...');

        try {
            const accommodationsData = await this.fetchJson(this.resourcesUrl);

            if (!accommodationsData) {
                console.error('Invalid response data:', accommodationsData);
                return [];
            }

            return accommodationsData?.map(Accommodation.copyConstructor);

        } catch (error) {
            console.error('Error fetching anime data:', error);
            return [];
        }
    }

    // async asyncFindById(id) /* :Promise<Account> */ {
    //     console.log('AccommodationAdaptor.asyncFindById(' + id + ')...');
    //     const accommodationsData = await this.fetchJson(this.resourcesUrl + "/" + id);
    //     return Accommodation.copyConstructor(accommodationsData);
    // }
    //
    // async asyncSave(anime) /* :Promise<Account> */ {
    //     console.log('TODO AnimeAdaptor.asyncSave(anime)...');
    //     return "TODO AccountsAdaptor.asyncSave";
    // }

}