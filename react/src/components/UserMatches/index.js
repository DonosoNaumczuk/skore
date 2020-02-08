import React, { Component } from 'react';
import Proptypes from 'prop-types';
import UserService from '../../services/UserService';
import Loader from '../Loader';
import UserMatches from './layout';
import ErrorPage from '../screens/ErrorPage';
import Utils from '../utils/Utils';

const INITIAL_OFFSET = 0;
const QUERY_QUANTITY = 1;

class UserMatchesContainer extends Component {
    mounted = false;
    constructor(props) {
        super(props);
        this.state = {
            matches: [],
            offset: INITIAL_OFFSET,
            limit: QUERY_QUANTITY,
            hasMore: true
        }
    }

    updateMatchesState = response => {
        if (response.status) {
            this.setState({
                status: response.status
            });
        }
        else {
            const hasMore = Utils.hasMorePages(response.links);
            const matches = response.matches;
            this.setState({
                matches: [...this.state.matches, ...matches],
                offset: this.state.offset + matches.length,
                hasMore: hasMore
            });
        }
    }

    getUserMatches = async (username) => {
        const { offset, limit } = this.state;
        const response = await UserService.getUserMatchesWithResults(username, offset, limit);
        if (this.mounted) {
            this.updateMatchesState(response)
        }
    }

    async componentDidMount() {
        this.mounted = true;
        this.getUserMatches(this.props.username);
        
    }

    render() {
        //TODO depending on users choice change between mach with result and only finished, and created not played
        const matches = this.state.matches;
        if (matches.length > 0 || !this.state.hasMore) {
            return (
                <UserMatches matches={matches} getUserMatches={this.getUserMatches}
                                hasMore={this.state.hasMore} username={this.props.username} />
            );
        }
        else if (this.state.status) {
            return (
                <ErrorPage status={this.state.status} />
            )
        }
        else {
            return (
                <Loader />//TODO improve with HOC
            );
        }
    }

    componentWillUnmount = () => {
        this.mounted = false;
        //TODO cancel fetch if still fetching matches
    }
}

UserMatchesContainer.propTypes = {
    username: Proptypes.string.isRequired
}

export default UserMatchesContainer;