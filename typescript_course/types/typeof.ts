const userName4 = "Wasa";
console.log(typeof userName4);

type UserName = typeof userName4;

const settings = {
    difficulty: "easy",
    minLevel: 10,
    didStart: false,
    players: ["Max", "Anna"]
};
type Settings = typeof settings; // typescript version of typeof

function loadData(settings: Settings) {
    // …
}

