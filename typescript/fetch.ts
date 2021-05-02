import fetch from 'node-fetch';
import HeadersInit from 'node-fetch';
import RequestInit from 'node-fetch';
import Response from 'node-fetch';

const url = "https://api.github.com/emojis";
   
const headers: HeadersInit = {
    "Accept": "application/vnd.github.v3+json"
}

const options : RequestInit = {
    method: 'GET',
    //headers,
    body: null
}

async function getEmojis(): Promise<string> {
    return fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error(response.statusText)
            }
            return response.json()
        });
}

getEmojis()
    .then(emojis => console.log(emojis));