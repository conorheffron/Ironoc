import React, { Component } from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

class About extends Component {

  render() {
    return (
        <div className="App">
          <AppNavbar/>
          <header className="App-header">
            <div className="App-intro">
              <h2>About</h2>
              <h4>Welcome, I'm Conor Heffron, a Software Engineer hailing from County Meath, Ireland.
              With over ten years of professional experience, I specialize in writing clean code and
              developing high-performance applications. As a passionate Full Stack Developer, I am constantly
              expanding my technical expertise across various tech stacks, languages, frameworks, and
              tools in the realm of Software, Data, and DevOps. Let's connect and explore exciting
              opportunities together! See below for contact details.</h4>
              <a class="btn btn-info" role="button" href="https://linktr.ee/conorheffron" target="_blank">Link Tree</a>
              <br/> <br/>
              <a href="https://www.linkedin.com/in/conorheffron"
                 target="_blank">
                 <img src="https://static.licdn.com/scds/common/u/img/webpromo/btn_viewmy_160x33.png" width="160" height="33" border="0"
                      alt="View Conor Heffron's profile on LinkedIn"></img>
               </a>
               <br/> <br/>
               <iframe height='160' width='300' frameborder='0'
                                   allowtransparency='true' scrolling='no'
                                   src='https://www.strava.com/athletes/2582329/activity-summary/a106933e6b6e42ffb4dcf37d2b5fe047af61329a'></iframe>
              </div>
          </header>
        </div>
    );
  }
}

export default About;