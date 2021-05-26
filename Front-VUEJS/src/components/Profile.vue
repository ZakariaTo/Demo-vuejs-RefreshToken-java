<template>
  <div>
    <v-file-input
      v-model="photo"
      :rules="rules"
      accept="image/png, image/jpeg, image/bmp"
      placeholder="Pick an avatar"
      prepend-icon="mdi-camera"
      label="Avatar"
    ></v-file-input>
    <v-btn v-on:click="uploadImage" color="primary">upload Image</v-btn>
  </div>
</template>
<script>
import Axios from "axios";
export default {
  data: () => ({
    photo: null,
    rules: [
      value =>
        !value ||
        value.size < 2000000 ||
        "Avatar size should be less than 2 MB!"
    ]
  }),
  methods: {
    uploadImage: function() {
      console.log("upload image");
      console.log("The image is: ", this.photo);
      let formData = new FormData();
      formData.append("file", this.photo);
      Axios.post(
        "http://localhost:8080/api/private/users/uploadavatar",
        formData
      )
        .then(() => {
          const objSnackBar = {
            text: "Image uploaded successefully",
            color: "success"
          };
          this.$store.dispatch("flashMessages", objSnackBar);
        })
        .catch(err => {
          console.log("Error : ", err);
          const objSnackBar = {
            text: "Something goes wrong while uploading",
            color: "#fc030f"
          };
          this.$store.dispatch("flashMessages", objSnackBar);
        });
    }
  }
};
</script>
