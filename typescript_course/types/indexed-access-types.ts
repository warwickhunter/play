type AppUser = {
    name: string;
    age: number;
    permissions: {
        id: string;
        title: string;
        description: string;
    }[];
};

// indexed access types
type Perms = AppUser["permissions"];
type Perm = Perms[number];

const perm: Perm = {
    id: "42",
    title: "admin",
    description: "can do everything"
}
const perms: Perms = [perm];
console.log(perm, perms)

type Names = string[];
type Name = Names[number];

