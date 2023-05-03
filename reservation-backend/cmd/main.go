package main

import (
	"fmt"
	"net/http"
	"os"

	"github.com/gorilla/mux"
)

func main() {
	serverPort := "8080"
	if port, hasValue := os.LookupEnv("API_PORT"); hasValue {
		serverPort = port
	}

	router := mux.NewRouter()
	//router.HandleFunc("/api/login", security.Login).Methods("POST")
	err := http.ListenAndServe(fmt.Sprintf(":%s", serverPort), router)
	fmt.Println(err)
}
