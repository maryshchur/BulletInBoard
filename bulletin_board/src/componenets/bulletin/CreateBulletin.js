// eslint-disable-next-line no-unused-vars
import React, {Component} from "react";
import axios from "../../utils/axios";
import DialogContent from "@material-ui/core/DialogContent";
import {TextField} from "@material-ui/core";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "@material-ui/core/Button";
import IconButton from "@material-ui/core/IconButton";

import AddAPhotoIcon from '@material-ui/icons/AddAPhoto';
import CssBaseline from "@material-ui/core/CssBaseline";
import Alert from "@material-ui/lab/Alert";

const textFieldStyles = {
    width: 300,
    minWidth: 100,
    maxWidth: 300
};

class CreateBulletin extends Component {
    state = {
        title: undefined,
        description: undefined,
        id: undefined,
        image: undefined,
        err: undefined
    };
    isNotValid = () => {
        return (this.state.title === undefined ||
            this.state.description === undefined || this.state.image === undefined);
    };
    saveBulletin = () => {
        let data = {};
        data.title = this.state.title;
        data.description = this.state.description;
        axios.post("/create-bulletin", data).then(response => {
            this.setState({id: response.data}, () => this.uploadImage());
        })
    };
    uploadImage = () => {
        const formData = new FormData();
        formData.append('file', this.state.image);
        axios.put(`/${this.state.id}/upload-photo`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).then(response => {
            this.props.handleClose();
        })
    };
    checkMimeType = (event) => {
        let files = event.target.files[0];
        if ((files.type !== 'image/png') && (files.type !== 'image/jpeg') && (files.type !== 'image/gif')) {
            this.setState({err: files.type + ' is not a supported format'});
        } else {
            this.setState({err: ('')});
            return true;
        }
    };


    checkFileSize = (event) => {
        let files = event.target.files[0];
        let size = 4000000;
        if (files.size > size) {
            this.setState({err: 'image is too large, please pick a smaller file'});
            return false
        } else {
            this.setState({err: ('')});
            return true;
        }
    };
    handleClickAddImage = (event) => {
        if (this.checkMimeType(event) && (this.checkFileSize(event))) {
            this.setState({
                image: event.target.files[0]
            })
        }
    };
    onChangeTitle = (event) => {
        this.setState({title: event.target.value});
    };
    onChangeDescription = (event) => {
        this.setState({description: event.target.value});
    };

    render() {
        return (
            <div>
                <DialogContent>
                    <CssBaseline/>
                    {this.state.err && <Alert severity="error">{this.state.err}</Alert>}
                    <TextField type="Title" style={textFieldStyles} label="Title"
                               onChange={this.onChangeTitle}
                               defaultValue={this.state.title}
                    />
                    <TextField type="description" label="Description" style={textFieldStyles}
                               onChange={this.onChangeDescription}
                               defaultValue={this.state.description}
                    />
                    <IconButton
                        color="primary"
                        component="label"
                    >
                        <AddAPhotoIcon/>
                        <input type='file' multiple='true'
                               style={{display: "none"}}
                               onChange={this.handleClickAddImage}
                        />
                    </IconButton>
                </DialogContent>
                <DialogActions>
                    <Button autoFocus onClick={this.props.handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={this.saveBulletin} disabled={this.isNotValid()} color="primary">
                        Save
                    </Button>
                </DialogActions>
            </div>
        );
    }
}

export default CreateBulletin;