import React from 'react';
import axios from 'axios';

class LoginForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            scope: 'read',
            grant_type: 'password',
            error: null
        };
        
        this.handleUsernameChange = this.handleUsernameChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleLoginSuccess = this.handleLoginSuccess.bind(this);
        this.handleLoginError = this.handleLoginError.bind(this);
    }
    
    handleSubmit(e) {
        //this.context.router.push('/new');
        //axios.post('http://localhost:8080/login', this.state)
        e.preventDefault();
        axios.post('/oauth/token', "", {
            params: this.state,
            auth: {
                username: 'admitone',
                password: 'admin'
            }, 
            withCredentials: true
        }) 
        .then(this.handleLoginSuccess)
        .catch(this.handleLoginError);
    }
    
    handleLoginSuccess(response) {
        //alert(JSON.stringify(response.data));
        localStorage.setItem('jwtToken', response.data.access_token)
        this.props.onClick();
    }
    
    handleLoginError(error) {
        //alert(JSON.stringify(error));
        if (error.response.data)
            this.setState({error: error.response.data});
        else
            this.setState({error: error});
    }
    
    handleUsernameChange(e) {
        this.setState({ username: e.target.value });
    }
    
    handlePasswordChange(e) {
        this.setState({ password: e.target.value });
    }

    render() {
        return (
          <div>
            
            { this.state.error && <div className="Error">{JSON.stringify(this.state.error)}</div> }
            
            <h1>Login</h1>
            <form onSubmit={this.handleSubmit} className="LoginForm">
              <div className="LoginForm">
                <label className="LoginForm"><b>Username:</b></label>
                <input type="text" placeholder="Enter Username" value={this.state.username} onChange={this.handleUsernameChange} required />
              </div>
              <div className="LoginForm">
                <label className="LoginForm"><b>Password:</b></label>
                <input type="password" placeholder="Enter Password" value={this.state.password} onChange={this.handlePasswordChange} required />
              </div>
              <button type="submit" className="LoginForm">Login</button>
            </form>
          </div>
        );
    }
}

export default LoginForm;