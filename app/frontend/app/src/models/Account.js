export class Account {
    id;
    firstName;
    lastName;
    email;
    password;
    fullName;
    role;
    profileImg;

    constructor(id, firstName, lastName, email, password, fullName, role, profileImg) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.profileImg = profileImg;
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
            this.fullName === other.fullName &&
            this.role === other.role &&
            this.profileImg === other.profileImg
    }

    static copyConstructor(account) {
        if (account === null || account === undefined) return null;
        return Object.assign(new Account(0), account);
    }

}