import http from "../http-common";

const FILE_API_BASE_URL = "/files";

class FileService {

    uploadFile(file) {
        let url = FILE_API_BASE_URL + "/upload";
        let formData = new FormData();
        formData.append("file", file);

        return http.post(url, formData, {
        headers: {
            "Content-Type": "multipart/form-data",
        }
        });
    }

    getAllDocs() {
        return http.get(FILE_API_BASE_URL);
    }

    deleteDoc(docId){
        return http.delete(FILE_API_BASE_URL + '/' + docId);
    }
}

export default new FileService()