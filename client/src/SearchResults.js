import React from 'react';

class SearchResults extends React.Component {

    render() {
    
        //const results = [{ customer:"bsawyer", eventId:1, count:2}
        //                 { customer:"mscott", eventId:1, count:1},
        //                 { customer:"bsawyer", eventId :2, count:1}]
        const results = this.props.results;
        
        return (
            <table className="SearchResults">
              <tbody>
                <tr>
                    <th>Event Id</th>
                    <th>Customer</th>
                    <th># Tickets</th>
                </tr>
                {results.map(function(result) {
                    return (
                        <tr key={result.eventId + result.customer}><td>{result.eventId}</td><td>{result.customer}</td><td>{result.count}</td></tr>
                    );
                }, [])}
              </tbody>
            </table>
            
        );
    }

}

export default SearchResults;