export class AccountsAdaptor {

    RESOURCES_URL;

    constructor(resourcesUrl) {
        this.RESOURCES_URL = resourcesUrl;
        // console.log("Created AccountsAdaptor for " + resourcesUrl);
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

}