import './App.css';
import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Col, Container, Row} from 'react-bootstrap';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';

import NavigationBar from "./components/NavigationBar";
import Welcome from "./components/Welcome";
import Footer from "./components/Footer";
import UserLogin from "./components/UserLogin";
import UserRegistration from "./components/creator/UserRegistration";
import Find from "./components/find/Find";
import FindById from "./components/find/FindById";
import FindByName from "./components/find/FindByName";
import FindByMail from "./components/find/FindByMail";
import AuthService from "./services/authService"
import NotFound from "./services/util/NotFound";
import Repo from "./components/Repo";
import AdminPage from "./components/AdminPage";
import NewDeck from "./components/creator/NewDeck";
import NewCard from "./components/creator/NewCard";
import Cards from "./components/Cards";


export default function App() {
    const marginTop = {
        marginTop: "20px"
    };

  return (
    <Router>
        <NavigationBar/>
        {AuthService.isLoggedIn() === true ?
            <Container>
                <Row>
                    <Col lg={12} style={marginTop}>
                        <Routes>
                            <Route exact path='/' element={<Welcome/>}/>
                            <Route path='/find' element={<Find/>}/>
                            <Route path='/find/findById' element={<FindById/>}/>
                            <Route path='/find/findByName' element={<FindByName/>}/>
                            <Route path='/find/findByMail' element={<FindByMail/>}/>
                            <Route path='/repo/newdeck' element={<NewDeck/>}/>
                            <Route path='/repo/newcard' element={<NewCard/>}/>
                            <Route path='/login' element={<UserLogin/>} />
                            <Route path='/registration' element={<UserRegistration/>}/>
                            <Route path='/repo' element={<Repo/>}/>
                            <Route path='/cards' element={<Cards/>}/>
                            {localStorage.getItem("user") === 'ADMIN' ? (
                                <Route path='/admin' element={<AdminPage/>}/>
                            ) :
                                <Route path="*" element={<NotFound />} />
                            }
                        </Routes>
                    </Col>
                </Row>
            </Container> :
            <Container>
                <Row>
                    <Col lg={12} style={marginTop}>
                        <Routes>
                            <Route exact path='/' element={<Welcome/>}/>
                            <Route path='/login' element={<UserLogin/>} />
                            <Route path='/registration' element={<UserRegistration/>}/>
                            <Route path="*" element={<NotFound />} />
                        </Routes>
                    </Col>
                </Row>
            </Container>
        }
        <Footer/>
    </Router>
  );
}
