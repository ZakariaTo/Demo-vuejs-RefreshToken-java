<template>
  <v-data-table :headers="headers" :items="users" sort-by="email" class="elevation-1">
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Users</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">New User</v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedItem.email"
                      label="Email"
                      :rules="emailRules"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedItem.password"
                      type="password"
                      label="Password"
                      :rules="passwordRules"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-select
                      v-model="editedItem.roles"
                      :items="rolesFromDataBase"
                      :rules="[v => !!v || 'Role is required']"
                      :item-text="'roleName'"
                      :item-value="'id'"
                      label="roles"
                      multiple
                    ></v-select>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text v-on:click="close">Cancel</v-btn>
              <v-btn color="blue darken-1" text @click="save">Save</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDelete" max-width="500px">
          <v-card>
            <v-card-title class="headline">Are you sure you want to delete this item?</v-card-title>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeDelete">Cancel</v-btn>
              <v-btn color="blue darken-1" text @click="deleteItemConfirm">OK</v-btn>
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:item.roles="{ item }">
      <v-chip
        v-for="role in item.roles"
        :key="role.id"
        :color="getColor(role.roleName)"
        dark
      >{{ role.roleName }}</v-chip>
    </template>
    <template v-slot:item.actions="{ item }">
      <v-icon small class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
      <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>
    </template>
    <template v-slot:no-data>
      <v-btn color="primary" @click="initialize">Reset</v-btn>
    </template>
  </v-data-table>
</template>

<script>
import Axios from "axios";
export default {
  data: () => ({
    passwordRules: [
      v => !!v || "Password is required",
      v => (v && v.length > 8) || "Password must be great than 8 characters"
    ],
    emailRules: [
      v => !!v || "E-mail is required",
      v => /.+@.+\..+/.test(v) || "E-mail must be valid"
    ],
    rolesFromDataBase: [],
    dialog: false,
    dialogDelete: false,
    headers: [
      {
        text: "Id",
        align: "start",
        sortable: false,
        value: "id"
      },
      { text: "Email", value: "email" },
      { text: "Roles", value: "roles" },
      { text: "Actions", value: "actions", sortable: false }
    ],
    users: [],
    editedIndex: -1,
    editedItem: {
      email: "",
      password: "",
      roles: []
    },
    defaultItem: {
      id: "",
      email: "",
      roles: []
    }
  }),
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "New User" : "Edit User";
    }
  },
  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.closeDelete();
    }
  },
  created() {
    this.initialize();
  },
  methods: {
    initialize() {
      Axios.get("http://localhost:8080/api/private/users/")
        .then(res => {
          this.users = res.data;
        })
        .catch(err => console.log("error getting users : ", err));
      Axios.get("http://localhost:8080/api/private/roles/")
        .then(res => {
          this.rolesFromDataBase = res.data;
        })
        .catch(err => console.log("error getting roles : ", err));
    },
    editItem(item) {
      this.editedIndex = this.users.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    deleteItem(item) {
      this.editedIndex = this.users.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },

    deleteItemConfirm() {
      this.users.splice(this.editedIndex, 1);
      this.closeDelete();
    },
    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    save() {
      if (this.formTitle === "New User") {
        if (this.editedItem.email === "" || this.editedItem.password === "") {
          const objSnackBar = {
            text: "Email Or Password are Required",
            color: "#fc030f"
          };
          this.$store.dispatch("flashMessages", objSnackBar);
          return;
        }
        Axios.post("http://localhost:8080/api/private/users/", {
          email: this.editedItem.email,
          password: this.editedItem.password,
          rolesId: this.editedItem.roles
        })
          .then(() => {
            this.close();
            const objSnackBar = {
              text: "User created succefully",
              color: "success"
            };
            this.$store.dispatch("flashMessages", objSnackBar);
            this.initialize();
          })
          .catch(err => {
            console.log("error", err);
            this.close();
            const objSnackBar = {
              text: "Email Already Exist",
              color: "#fc030f"
            };
            this.$store.dispatch("flashMessages", objSnackBar);
          });
        return;
      }
      if (this.formTitle === "Edit User") {
        if (this.editedItem.email === "") {
          const objSnackBar = {
            text: "Email is Required",
            color: "#fc030f"
          };
          this.$store.dispatch("flashMessages", objSnackBar);
          return;
        }
        if (
          this.editedItem.roles.length &&
          this.editedItem.roles[0].roleName != undefined
        ) {
          const rolesId = [];
          this.editedItem.roles.map(role => {
            rolesId.push(role.id);
          });
          this.editedItem.roles = rolesId;
        }
        Axios.put(
          "http://localhost:8080/api/private/users/" +
            this.users[this.editedIndex].id,
          {
            email: this.editedItem.email,
            password: this.editedItem.password,
            rolesId: this.editedItem.roles
          }
        )
          .then(res => {
            if (res) {
              this.close();
              const objSnackBar = {
                text: "User updated succefully",
                color: "success"
              };
              this.$store.dispatch("flashMessages", objSnackBar);
              this.initialize();
            }
          })
          .catch(err => {
            const objSnackBar = {
              text: err,
              color: "#fc030f"
            };
            this.$store.dispatch("flashMessages", objSnackBar);
          });
        return;
      }
    },
    getColor(roleName) {
      if (roleName.toUpperCase() === "ADMIN") return "red";
      else if (roleName.toUpperCase() === "GUEST") return "green";
      else return "orange";
    }
  }
};
</script>