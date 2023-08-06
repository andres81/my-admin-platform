import './App.css';
import { Router, Route, Switch } from "react-router-dom";
import { useAuth0 } from "@auth0/auth0-react";

import { Container } from "reactstrap";
import { createBrowserHistory } from "history";

import Home from "./views/Home"
import Profile from "./views/Profile"

import NavBar from './components/NavBar'

function App() {

  const { error } = useAuth0();
  const history = createBrowserHistory();

  if (error) {
    return <div>Oops... {error.message}</div>;
  }

  return (
    <div className="App">
      <Router history={history}>
      <div id="app" className="d-flex flex-column h-100">
        <NavBar />
        <Container className="flex-grow-1 mt-5">
          <Switch>
            <Route path="/" exact component={Home} />
            <Route path="/profile" component={Profile} />
            {/*<Route path="/external-api" component={ExternalApi} />*/}
          </Switch>
        </Container>
        {/*<Footer />*/}
      </div>
    </Router>
    </div>
  );
}

export default App;
