import React, { Component } from 'react';
import LoginForm from './LoginForm';
import SearchPage from './SearchPage';
import './App.css';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {isLoggedIn: false};
    this.handleLoginClick = this.handleLoginClick.bind(this);
  }
  
  handleLoginClick() {
    this.setState({isLoggedIn: true});
  }
  
  render() {
    const isLoggedIn = this.state.isLoggedIn;
    
    let page = null;
    if (!isLoggedIn) {
        page = <LoginForm onClick={this.handleLoginClick} />;
    } else {
        page = <SearchPage />;
    }
    return (
      <div className="App">
        <div className="App-header">
          <h2>AdmitOne</h2>
        </div>
        <div className="Main">
          {page}
        </div>
      </div>
    );
  }
}

export default App;
