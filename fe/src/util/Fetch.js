export async function postData(url = "", data = {}, token = "") {
  // Default options are marked with *
  const response = await fetch(url, {
    method: "POST", // *GET, POST, PUT, DELETE, etc.
    mode: "cors", // no-cors, *cors, same-origin
    cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
    credentials: "same-origin", // include, *same-origin, omit
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    },
    redirect: "follow", // manual, *follow, error
    referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
    body: JSON.stringify(data), // body data type must match "Content-Type" header
  });
  return response.json(); // parses JSON response into native JavaScript objects
}

export async function getData(url = "", token = "") {
  const response = await fetch(url, {
    mode: "cors",
    cache: "no-cache",
    headers: {
      "Authorization": `Bearer ${token}`
    },
    referrerPolicy: "no-referrer",
  }).catch(e => {
    console.error(`Download error: ${e.message}`);
  });
  return response.json(); // parses JSON response into native JavaScript objects
}
