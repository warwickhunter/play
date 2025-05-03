// template literals
const mainUserName = "Max";
const greeting = `Hello, ${mainUserName}!`;
console.log(greeting);

// template literal types
type ReadPermission = "no-read" | "read";
type WritePermission = "no-write" | "write";
type FilePermissions = `${ReadPermission}-${WritePermission}`;

type DataFile = {
    data: string;
    permissions: FilePermissions;
}
type DataFileEventNames = `${keyof DataFile}Changed`;
type DataFileEvents = {
    [Key in DataFileEventNames]: () => void;
};
