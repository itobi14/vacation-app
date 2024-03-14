export class Accommodation {
    id;
    name;
    country;
    city;
    streetNameNr;
    zipCode;
    price;
    type;
    description;
    imgUrl;

    constructor(id, name, country, city, streetNameNr, zipCode, price, type, description, imgUrl) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.streetNameNr = streetNameNr;
        this.zipCode = zipCode;
        this.price = price;
        this.type = type;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    getAddress(streetNameNr, zipCode) {
        return streetNameNr + " " + zipCode;
    }

    static copyConstructor(accommodation) {
        if (accommodation === null || accommodation === undefined) return null;
        return Object.assign(new Accommodation(), accommodation);
    }

    equalsById(other) {
        return other != null && this.id === other.id;
    }

    equals(other) {
        return other != null &&
            this.id === other.id &&
            this.name === other.name &&
            this.country === other.country &&
            this.city === other.city &&
            this.streetNameNr === other.streetNameNr &&
            this.zipCode === other.zipCode &&
            this.price === other.price &&
            this.type === other.type &&
            this.description === other.description &&
            this.imgUrl === other.imgUrl
    }

}