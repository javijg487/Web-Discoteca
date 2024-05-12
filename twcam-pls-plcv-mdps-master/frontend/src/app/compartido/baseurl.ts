import { HttpHeaders } from "@angular/common/http";

export const baseURL = "http://localhost:3000/";
export const baseAPIURL = "http://127.0.0.1:8080/proyecto-discoteca/api/"; // <1>

export const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
    Authorization: "my-auth-token",
  }),
};
