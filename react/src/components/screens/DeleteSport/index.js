import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import PropTypes from 'prop-types';
import AuthService from '../../../services/AuthService';
import Spinner from '../../Spinner';
import SportService from '../../../services/SportService';

class DeleteSport extends Component {
    mounted = false;
    constructor(props) {
        super(props);
        const { sportName } = props.match.params;
        this.state = {
            executing: true,
            sportName: sportName
        } 
    }

    componentDidMount = async () => {
        this.mounted = true;
        const response = await SportService.deleteSport(this.state.sportName);
        if (response.status) {
            if (this.mounted) {
                this.setState({ error: response.status });
                //TODO check for status of sport with played games
            }
        }
        else if (this.mounted) {
                this.setState({ executing: false });
        }
    }

    render() {
        const isAdmin = AuthService.isAdmin();
        if (!isAdmin) {
            return <Redirect to="/error/403" />;
        }
        else if (this.state.error) {
            return <Redirect to={`/error/${this.state.error}`} />;
        }
        else if (this.state.executing) {
            return <Spinner />;
        }
        else {
            return <Redirect to='/admin' />;
        }
    }

    componentWillUnmount = () => {
        this.mounted = false;
    }
} 

DeleteSport.propTypes = {
    match: PropTypes.object.isRequired
}

export default DeleteSport;