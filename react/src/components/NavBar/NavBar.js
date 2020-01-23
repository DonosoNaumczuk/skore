import React from 'react';
import Proptypes from 'prop-types';
import NavBarForm from './NavBarForm';
import { Link } from 'react-router-dom';

//TODO maybe use link instead of href an check that is valid to use href directly
// i dont remember why was it that in jsp we used to put <c:url> and here we use it directly
const NavBar = (props) => {
    return (
        <React.Fragment>
            <nav className="navbar fixed-top primary-nav ">
                <button className="navbar-toggler d-md-none" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="fas fa-bars"></span>
                </button>
                <Link className="d-none d-sm-block navbar-brand nav-brand-font" to="/">sk<i className="fas fa-bullseye"></i>re</Link>
                <Link className="d-sm-none navbar-brand nav-brand-font" to="/"><i className="fas fa-bullseye"></i>sk</Link>
                <Link className="d-sm-none login-link" to="/"><i className="fas fa-user"></i></Link>
                <NavBarForm currentUser={props.currentUser} /> 
            </nav>
        </React.Fragment>
    )
}

NavBar.propTypes = {
    currentUser: Proptypes.string
}

export default NavBar;