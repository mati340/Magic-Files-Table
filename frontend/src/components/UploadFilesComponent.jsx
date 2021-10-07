import React, { Component } from 'react';
import FileService from '../services/FileService';

import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

class UploadFilesComponent extends Component {

    constructor(props) {
        super(props);

        this.state = {
            selectedFile: null
        }
    }

    onFormSubmit = event =>{
        event.preventDefault() // Stop from double submit

        if (this.state.selectedFile === null){
            toast.error("Please select file!");
            return;
        }
        this.uploadFileHandler(this.state.selectedFile)
        .then((response)=>{
            toast.success("File uploaded successfully!");
            this.setState({ selectedFile: null });
            document.getElementById("myFile").value = "";

            this.props.parentCallback("Reload files list!"); // Reload files list through parent
            })
        .catch((error) => {
            toast.error(error.response.data);
            this.props.parentCallback("Reload files list!"); // Reload files list through parent
            });
    }

    changeFileHandler = event => {
        if (event.target.files.length > 0) { // file has selected
            this.setState({ selectedFile: event.target.files[0] });
        }
        else // no file selected
        {
            toast.warn("No file selected!");
        }
    }

    uploadFileHandler = (file) => {
        return FileService.uploadFile(file);
    }

    render() {
        return (
            <div style={{marginTop: '30px'}} className="container">
                <h1 className="text-center">File Upload</h1>
                <form style={{marginTop: '30px', display: 'flex', justifyContent: 'center'}} onSubmit={this.onFormSubmit}>
                    <input id="myFile" type="file" onChange={this.changeFileHandler} />
                    <button type="submit">Upload</button>
                </form>
            </div>
        );
    }
}

export default UploadFilesComponent;