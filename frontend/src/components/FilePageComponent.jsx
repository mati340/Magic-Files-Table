import React, { Component } from 'react';
import ListFilesComponent from './ListFilesComponent';
import UploadFilesComponent from './UploadFilesComponent';
import { ToastContainer } from 'react-toastify';

class FilePageComponent extends Component {

    // binding from UploadFilesComponent to ListFilesComponent
    callbackFunction = () => {
        this.wakeChild()
    }

    render() {
        return (
            <div>
                <UploadFilesComponent parentCallback = {this.callbackFunction}/>
                <ListFilesComponent reloadList={func => this.wakeChild = func}/> 
                <ToastContainer />
            </div>
        );
    }
}

export default FilePageComponent;