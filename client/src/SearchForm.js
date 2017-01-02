import React from 'react';
import axios from 'axios';

class SearchForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            start: '',
            end: '',
            error: null
        };
        
        this.handleStartChange = this.handleStartChange.bind(this);
        this.handleEndChange = this.handleEndChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleError = this.handleError.bind(this);
    }
    
    handleSubmit(e) {
        e.preventDefault();
        this.setState({error: null});
        axios.get('/admin/search/' + this.state.start + '/' + this.state.end, {
            headers: {'Authorization': 'bearer' + localStorage.getItem('jwtToken')}
        })
        .then(this.props.onClick)
        .catch(this.handleError);
    }
    
    handleError(error) {
        //alert(JSON.stringify(error));
        if (error.response.data)
            this.setState({error: error.response.data});
        else
            this.setState({error: error});
    }
    
    handleStartChange(e) {
        this.setState({ start: e.target.value });
    }
    
    handleEndChange(e) {
        this.setState({ end: e.target.value });
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                { this.state.error && <div className="Error">{JSON.stringify(this.state.error)}</div> }
            
                <label>Search for events between</label>
                <div>
                    <input type="text" placeholder="Event ID Start" value={this.state.start} onChange={this.handleStartChange} required className="Search" />
                    <label>and</label>
                    <input type="text" placeholder="Event ID End" value={this.state.end} onChange={this.handleEndChange} required className="Search" />
                </div>
                <div>
                  <button type="submit">Search</button>
                </div>
            </form>
        );
    }
}

export default SearchForm;