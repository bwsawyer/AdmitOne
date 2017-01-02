import React from 'react';
import SearchForm from './SearchForm';
import SearchResults from './SearchResults';

class SearchPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            results: []
        };
        this.handleSearchClick = this.handleSearchClick.bind(this);
    }
    
    handleSearchClick(results) {
      //alert(JSON.stringify(results));
      this.setState({results: results.data});
    }

    render() {
        return (
            <div>
                <SearchForm onClick={this.handleSearchClick}/>
                {this.state.results[0] && 
                    <div>
                        <SearchResults results={this.state.results}/>
                    </div>
                }
            </div>
        );
    }
}

export default SearchPage;