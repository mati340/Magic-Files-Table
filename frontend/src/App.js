import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import FilePageComponent from './components/FilePageComponent';

function App() {
  return (
    <Router>
          <div className="container">
              <Switch> 
                <Route path="/">
                  <FilePageComponent />
                </Route>  
              </Switch>
          </div>
    </Router>
  );
}

export default App;
