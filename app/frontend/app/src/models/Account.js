export class Account {
    id;
    name;
    email;
    role;

    constructor(id, name = null) {
        this.id = id;
        this.name = name;
    }

    equalsById(other) {
        return other != null && this.id === other.id;
    }
    equals(other) {
        return other != null &&
            this.id === other.id &&
            this.name === other.name &&
            this.email === other.email &&
            this.role === other.role
    }

    static copyConstructor(account) {
        if (account === null || account === undefined) return null;
        return Object.assign(new Account(0), account);
    }

    static createSample(id) {
        let newAccount = new Account(id, `account${id}`);
        newAccount.email = `account${id}@hva.nl`;
        newAccount.role = Math.random() < 0.3 ? "administrator" : "registered user";
        return newAccount;
    }
}