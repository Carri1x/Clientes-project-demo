import { bootstrapApplication } from '@angular/platform-browser';
import { App } from './app/app';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { provideHttpClient } from '@angular/common/http';

bootstrapApplication(App, {
  providers: [
    provideRouter(routes),
    provideHttpClient(), // ✅ moderno y sin deprecación
  ],
});

// This code bootstraps the Angular application, providing the necessary routes for navigation.
// The `App` component serves as the root component of the application, and the routes are provided to enable navigation between different components, such as the login page.
// The `bootstrapApplication` function initializes the application, allowing it to run in the browser.
// The `provideRouter` function is used to set up the routing configuration, enabling the application to respond to different URL paths and display the corresponding components.
// This setup is essential for creating a single-page application (SPA) where users can navigate between
// different views without reloading the entire page, enhancing the user experience by providing a seamless interaction flow.
// The application is structured to allow for easy expansion, where additional components and routes can be added in the future.
// The `App` component is the main entry point of the application, and it can include other components like `Login` for user authentication.
// The `routes` array defines the paths and associated components, allowing for modular and organized code
// structure, making it easier to maintain and scale the application as needed.

