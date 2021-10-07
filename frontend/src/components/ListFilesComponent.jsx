import React, { Component } from 'react';
import {DragDropContext, Droppable, Draggable} from "react-beautiful-dnd";
import FileService from '../services/FileService';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

class ListFilesComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            files: [],
            sumAmount: null
        };
        this.getAllDocs = this.getAllDocs.bind(this);
    }

    // delete doc in case row drag outside if the table
    dragEndHandle = (results) => {
        if( !results.destination){
            this.deleteDoc(this.state.files[results.source.index]);
            return;
        }

        let tempFile = [...this.state.files];
        let [selectedRow] = tempFile.splice(results.source.index, 1); // remove the selected row
        tempFile.splice(results.destination.index, 0, selectedRow); // insert the selected row to destination
        this.setState({files: tempFile});
    }

    deleteDoc = (fileToDelete) => {
        
        FileService.deleteDoc(fileToDelete.docId)
        .then( res => {
            this.setState({files: this.state.files.filter(file => file.docId !== fileToDelete.docId)}, () => { 
                this.calculateSumAmounts()
                toast.success("Doc with docID: " + fileToDelete.docId + " has deleted successfully")
            })})
        .catch( err => {
            toast.error(err.response.data);
            this.getAllDocs();
            });
    }

    getAllDocs(){
        FileService.getAllDocs()
        .then((res) => {
            this.setState({ files: res.data},() => {this.calculateSumAmounts()});
            })
        .catch( err => {
            toast.error(err.message);
            });
    }

    componentDidMount(){
        this.props.reloadList(this.getAllDocs); // bind with parent
        this.getAllDocs();
    }

    calculateSumAmounts = () => {
        let tempSum = null; // for no docs case
        if(this.state.files.length > 0){
            tempSum = 0;
        }

        this.state.files.forEach(file => {
            let tempAmount = Number.parseInt(file.amount);

            if(file.sign === '+'){
                tempSum += tempAmount;
            }
            else if (file.sign === '-'){
                tempSum -= tempAmount;
            }
        });

        this.setState({ sumAmount: tempSum});
    }

    render() {
        return (
            <div style={{marginTop: '100px'}}>
                <h1 className="text-center">Files List</h1>
                <div className = "row">
                    <table style={{ textAlign: "center", marginBottom: "30px", marginTop: "30px"}} className = "table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>Amount Summary</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>{this.state.sumAmount === null ? "No documents" : this.state.sumAmount}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <DragDropContext onDragEnd={(results) => this.dragEndHandle(results)}>
                    <div className = "row">
                    <table style={{ textAlign: "center", marginBottom: "100px" }} className = "table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th> DocType</th>
                                <th> CompanyID</th>
                                <th> Date</th>
                                <th> DocID</th>
                                <th> Amount</th>
                            </tr>
                        </thead>
                        <Droppable droppableId="listFiles" >
                            {(provided) => (
                                <tbody ref={provided.innerRef} {...provided.droppableProps}>
                                    {
                                    this.state.files.map(
                                        (file, index) => 
                                        <Draggable draggableId={file.docId.toString()} index={index} key={file.docId}>
                                            {
                                                (provided) => (
                                                    <tr ref={provided.innerRef} {...provided.draggableProps} {...provided.dragHandleProps}>
                                                        <td>{file.docType}</td>   
                                                        <td>{file.companyId}</td>
                                                        <td>{file.date.substr(0,10)}</td>
                                                        <td>{file.docId}</td>
                                                        <td>{file.sign ==='-' ? file.sign + file.amount : file.amount}</td>
                                                    </tr>
                                                )
                                            }
                                        </Draggable>
                                    )
                                    }  
                                    {provided.placeholder}                          
                                </tbody>
                            )}
                        </Droppable>
                    </table>
                </div>
                </DragDropContext>
            </div>
        );
    } 
}

export default ListFilesComponent;