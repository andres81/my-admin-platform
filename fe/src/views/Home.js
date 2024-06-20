import React, { useState } from "react";
import { useAuth0} from "@auth0/auth0-react";

import {getData} from '../util/Fetch'

const Home = () => {

  let apiOrigin = "http://localhost:8080";

  const {
    getAccessTokenSilently,
  } = useAuth0();

  const [state, setState] = useState({
    showResult: false,
    apiMessage: "",
    error: null,
  });

const callApi = async () => {
      const token = await getAccessTokenSilently();
      const responseData = await getData(`${apiOrigin}/api/map-user-daot/test`, token);
      console.info('received responseData: ', responseData);
      setState({
        ...state,
        showResult: true,
        apiMessage: responseData,
      });
    };

  return (
    <div>
        <h1>Welcome Home!</h1>
        <button onClick={callApi}>
            Make API call!
        </button>
        <div id="server-response">
            {state.error}
            JSON.stringify(state.apiMessage, null, 2)
        </div>
    </div>
  );
};

export default Home;
