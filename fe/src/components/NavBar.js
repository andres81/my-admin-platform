import React from "react";

import LoginButton from './LoginButton'
import LogoutButton from './LogOutButton'

export default function NavBar() {

    return (
            <ul className="nav justify-content-end">
              <li>Profile</li>
              <LoginButton/>
              <LogoutButton />
            </ul>
    );
}
