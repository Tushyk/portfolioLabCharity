document.addEventListener("DOMContentLoaded", function() {

  const address = document.querySelector('#address');
  const li = document.createElement("li");
  const li1 = document.createElement("li");
  const li2 = document.createElement("li");
  const li3 = document.createElement("li");
  const delivery = document.querySelector('#delivery');
  const li00 = document.createElement("li");
  const li01 = document.createElement("li");
  const li03 = document.createElement("li");
  address.appendChild(li);
  address.appendChild(li1);
  address.appendChild(li2);
  address.appendChild(li3);

  delivery.appendChild(li00);
  delivery.appendChild(li01);
  delivery.appendChild(li03);
  /**
   * Form Select
   */
  class FormSelect {
    constructor($el) {
      this.$el = $el;
      this.options = [...$el.children];
      this.init();
    }

    init() {
      this.createElements();
      this.addEvents();
      this.$el.parentElement.removeChild(this.$el);
    }

    createElements() {
      // Input for value
      this.valueInput = document.createElement("input");
      this.valueInput.type = "text";
      this.valueInput.name = this.$el.name;

      // Dropdown container
      this.dropdown = document.createElement("div");
      this.dropdown.classList.add("dropdown");

      // List container
      this.ul = document.createElement("ul");

      // All list options
      this.options.forEach((el, i) => {
        const li = document.createElement("li");
        li.dataset.value = el.value;
        li.innerText = el.innerText;

        if (i === 0) {
          // First clickable option
          this.current = document.createElement("div");
          this.current.innerText = el.innerText;
          this.dropdown.appendChild(this.current);
          this.valueInput.value = el.value;
          li.classList.add("selected");
        }

        this.ul.appendChild(li);
      });

      this.dropdown.appendChild(this.ul);
      this.dropdown.appendChild(this.valueInput);
      this.$el.parentElement.appendChild(this.dropdown);
    }

    addEvents() {
      this.dropdown.addEventListener("click", e => {
        const target = e.target;
        this.dropdown.classList.toggle("selecting");

        // Save new value only when clicked on li
        if (target.tagName === "LI") {
          this.valueInput.value = target.dataset.value;
          this.current.innerText = target.innerText;
        }
      });
    }
  }
  document.querySelectorAll(".form-group--dropdown select").forEach(el => {
    new FormSelect(el);
  });

  /**
   * Hide elements when clicked on document
   */
  document.addEventListener("click", function(e) {
    const target = e.target;
    const tagName = target.tagName;

    if (target.classList.contains("dropdown")) return false;

    if (tagName === "LI" && target.parentElement.parentElement.classList.contains("dropdown")) {
      return false;
    }

    if (tagName === "DIV" && target.parentElement.classList.contains("dropdown")) {
      return false;
    }

    document.querySelectorAll(".form-group--dropdown .dropdown").forEach(el => {
      el.classList.remove("selecting");
    });
  });

  /**
   * Switching between form steps
   */
  class FormSteps {
    constructor(form) {
      this.$form = form;
      this.$next = form.querySelectorAll(".next-step");
      this.$prev = form.querySelectorAll(".prev-step");
      this.$step = form.querySelector(".form--steps-counter span");
      this.currentStep = 1;

      this.$stepInstructions = form.querySelectorAll(".form--steps-instructions p");
      const $stepForms = form.querySelectorAll("form > div");
      this.slides = [...this.$stepInstructions, ...$stepForms];

      this.init();
    }

    /**
     * Init all methods
     */
    init() {
      this.events();
      this.updateForm();
    }

    /**
     * All events that are happening in form
     */
    events() {
      // Next step
      this.$next.forEach(btn => {
        btn.addEventListener("click", e => {
          e.preventDefault();
          this.currentStep++;
          this.updateForm();
        });
      });

      // Previous step
      this.$prev.forEach(btn => {
        btn.addEventListener("click", e => {
          e.preventDefault();
          this.currentStep--;
          this.updateForm();
        });
      });

      // Form submit
      this.$form.querySelector("form").addEventListener("submit", e => this.submit(e));
    }

    /**
     * Update form front-end
     * Show next or previous section etc.
     */
    updateForm() {
      this.$step.innerText = this.currentStep;

      // TODO: Validation

      this.slides.forEach(slide => {
        slide.classList.remove("active");

        if (slide.dataset.step == this.currentStep) {
          slide.classList.add("active");
        }
      });

      this.$stepInstructions[0].parentElement.parentElement.hidden = this.currentStep >= 5;
      this.$step.parentElement.hidden = this.currentStep >= 5;

      // TODO: get data from inputs and show them in summary
      let quantity = document.getElementById("bags").value;
      let institution;
      document.querySelectorAll('#institutions').forEach(function (element) {
        if (element.checked === true) {
          let text = element.parentElement.lastElementChild.firstElementChild.innerText;
          institution = text.substring(8, text.length).trim();
        }
      })
      document.getElementById("summaryFundations").innerText = "Dla fundacji " + institution;

      let categories = [];
      document.querySelectorAll('#categories').forEach(function (element) {
        if (element.checked === true) {
          categories.push(element.parentElement.lastElementChild.innerText.trim());
        }
      })
      if (quantity === 1) {
        document.getElementById("summaryBags").innerText = quantity +" worek: " + categories.toString();
      } else if (quantity > 1 && quantity < 5) {
        document.getElementById("summaryBags").innerText = quantity +" worki: " + categories.toString();
      } else if (quantity >= 5) {
        document.getElementById("summaryBags").innerText = quantity +" workow: " + categories.toString();
      }

      li.innerText = document.getElementById("street").value;
      li1.innerText = document.getElementById("city").value;
      li2.innerText = document.getElementById("zipCode").value;
      li3.innerText = document.getElementById("phoneNumber").value;
      li00.innerText = document.getElementById("pickUpDate").value;
      li01.innerText = document.getElementById("pickUpTime").value;
      li03.innerText = document.getElementById("pickUpComment").value;
    }

  }
  const form = document.querySelector(".form--steps");
  if (form !== null) {
    new FormSteps(form);
  }
});
