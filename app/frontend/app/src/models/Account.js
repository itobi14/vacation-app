export class Account {
    id;
    firstName;
    lastName;
    email;
    password;
    profileImg;
    fullName;

    constructor(id, firstName, lastName, email, password, profileImg, fullName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.fullName = fullName;
    }

    equalsById(other) {
        return other != null && this.id === other.id;
    }

    equals(other) {
        return other != null &&
            this.id === other.id &&
            this.firstName === other.firstName &&
            this.lastName === other.lastName &&
            this.email === other.email &&
            this.password === other.password &&
            this.profileImg === other.profileImg &&
            this.fullName === other.fullName
    }

    static copyConstructor(account) {
        if (account === null || account === undefined) return null;
        return Object.assign(new Account(0), account);
    }

}