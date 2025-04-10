type FileSource = { path: string };
const fileSource: FileSource = {
    path: 'some/path/to/file.csv',
};

type DBSource = { connectionUrl: string };
const dbSource: DBSource = {
    connectionUrl: 'some-connection-url',
};

type Source = FileSource | DBSource;

function loadData(source: Source) {
    if ('path' in source) {
        console.log(`Using ${source.path}`);
        return
    }
    console.log(`Using ${source.connectionUrl}`);
}

loadData(fileSource);
loadData(dbSource);

// discriminated union

type FileSource2 = { type: 'file', path: string };
type DBSource2 = { type: 'db', connectionUrl: string };
const fileSource2: FileSource2 = {
    type: 'file',
    path: 'some/path/to/file2.csv',
};
const dbSource2: DBSource2 = {
    type: 'db',
    connectionUrl: 'some-connection-url2',
};

type Source2 = FileSource2 | DBSource2;

function isFile(source: Source2) {
    return source.type === 'file';
}

function loadData2(source: Source2) {
    if (isFile(source)) {
        console.log(`Using ${source.path}`);
        return
    }
    console.log(`Using ${source.connectionUrl}`);
}

loadData2(fileSource2);
loadData2(dbSource2);

class User7 {
    constructor(public name: string) {}

    join() {
        // ...
    }
}

class Admin {
    constructor(permissions: string[]) {}

    scan() {
        // ...
    }
}

const user7 = new User7('Max');
const admin = new Admin(['ban', 'restore']);

type Entity = User7 | Admin;

function init(entity: Entity) {
    if (entity instanceof User7) {
        entity.join();
        return;
    }
    entity.scan();
}