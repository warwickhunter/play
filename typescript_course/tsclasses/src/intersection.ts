type FileData = {
    path: string;
    content: string;
};

type Status = {
    isOpen: boolean;
    errorMessage?: string;
};

// intersection type
type AccessedFileData = FileData & Status;

// this could be done instead with interfaces, probably a better choice.