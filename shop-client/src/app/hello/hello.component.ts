import { Component, OnInit } from '@angular/core';
import {KeycloakService} from "keycloak-angular";
import {HttpClient} from "@angular/common/http";
import {Order} from "../model/Order";

@Component({
  selector: 'app-hello',
  templateUrl: './hello.component.html',
  styleUrls: ['./hello.component.css']
})
export class HelloComponent {

  constructor(private securityService: KeycloakService, private httpClient: HttpClient) {
  }

  logout() {
     this.securityService.logout("http://localhost:4200")
  }

  getOrder() {
    this.httpClient.get<Order>("http://localhost:8001/orders/3")
      .subscribe(order => console.log(order), ex => console.log(ex));
  }

}
