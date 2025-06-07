export class User {
    name: string | undefined;
    cpf: string | undefined;
    password: string | undefined;
    phoneNumber: string | undefined;

    constructor(name?: string, cpf?: string, password?: string, phoneNumber?: string) {
        this.name = name;
        this.cpf = cpf;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}