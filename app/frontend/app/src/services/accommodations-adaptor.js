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
            console.error('Error fetching accommodations data:', error);
            return [];
        }
    }

    async findById(id){

        if (id === undefined) {
            console.error("ID is undefined");
            return null; // or throw an error or handle it as appropriate
        }

        // console.log('AccommodationAdaptor.asyncFindById(' + id + ')...');
        const accommodationData = await this.fetchJson(this.resourcesUrl + "/" + id);
        return Accommodation.copyConstructor(accommodationData);
    }

}