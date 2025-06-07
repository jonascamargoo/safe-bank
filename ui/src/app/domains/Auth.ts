import { User } from "./User";

export class Auth{
    cpf: string | undefined;
    password: string | undefined;

    constructor(user:User){
        this.cpf = user.cpf;
        this.password = user.password;
    }
}