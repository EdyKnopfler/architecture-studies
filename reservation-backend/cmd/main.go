package main

import (
	"fmt"
	"net/http"
	"os"

	"com.derso/travelagency/reservation/security"
	"github.com/gorilla/mux"
)

func main() {
	serverPort := "8080"
	if port, hasValue := os.LookupEnv("API_PORT"); hasValue {
		serverPort = port
	}

	security.GetKeys()

	router := mux.NewRouter()
	router.HandleFunc("/login", security.Login).Methods("POST")
	err := http.ListenAndServe(fmt.Sprintf(":%s", serverPort), router)
	fmt.Println(err)
}
