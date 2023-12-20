import { HttpHeaders } from "@angular/common/http";

export const baseURL = "http://localhost:3000/";
export const baseAPIURL = "http://localhost:8080/proyecto-discoteca/api/"; // <1>

export const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
    Authorization: "my-auth-token",
  }),
};
