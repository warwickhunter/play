// interface
interface Authenticatable {
    email: string;
    password: string;

    login(): void;
    logout(): void;
}

// interface as an object type
let user: Authenticatable;
user = {
    email: 'test@example.com',
    password: 'secret',
    role: 'developer',
    login(): void {
        // …
    },
    logout(): void {
        // …
    }
}

// declaration merging extends an existing interface
interface Authenticatable {
    role: string;
}

// implements interface
class AuthenticableUser implements Authenticatable {
    constructor(public email: string, public password: string, public role: string) {
    }

    login() {
        // …
    }

    logout() {
        // …
    }
}
function authenticate(user: Authenticatable) {
    user.login()
}

// extend interface
interface AuthenticatableAdmin extends Authenticatable {
    role: 'admin' | 'superadmin';
}