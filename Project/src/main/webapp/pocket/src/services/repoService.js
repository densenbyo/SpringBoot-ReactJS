import axios from "axios";

const API_URL = "http://localhost:8080/repo/";

class RepoService {
    getAllRepo(){
        return axios.get(API_URL + "all")
    }

    getCurrentRepo(id){
        return axios.get(API_URL + id, {
            headers: {
                'Authorization' : 'Bearer ' + localStorage.getItem("token")
            }
        })
    }

    getAllCards(id){
        return axios.get(API_URL + id + "/flashcards", {
            headers: {
                'Authorization' : 'Bearer ' + localStorage.getItem("token")
            }
        })
    }

    getAllDecks(id){
        return axios.get(API_URL + id + "/flashcardDecks", {
            headers: {
                'Authorization' : 'Bearer ' + localStorage.getItem("token")
            }
        })
    }

    addNewDeck(name, description, isPublic){
        return axios.patch(API_URL + localStorage.getItem("userID") + "/flashcardDecks", {
            name, description, isPublic
        }, {
            headers: {
                'Authorization' : 'Bearer ' + localStorage.getItem("token")
            }
        })
            .then(response => {
                console.log(response.data);
            });
    }

    deleteDeck(id, deckId){
        return axios.patch(API_URL + "repo_" + id +"/removeDeck/" + deckId)
            .then(() => {
                window.location.reload();
            });
    }

    addNewCard(id, word, translation){
        return axios.post(API_URL + "repo_" + id +"/addCard/", {word, translation})
            .then(response => {
                console.log(response.data);
            });
    }

    deleteCard(id, cardId){
        return axios.patch(API_URL + "repo_" + id +"/deleteCard/" + cardId)
            .then(() => {
                window.location.reload();
            });
    }
}

export default new RepoService();