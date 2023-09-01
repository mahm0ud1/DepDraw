import { BrowserRouter, Routes, Route } from "react-router-dom";
import UserLayout from "../../components/layouts/userLayout/UserLayout";

import HomePage from "../homePage/HomePage"

function AppRoutes() {

  return (
      <>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<UserLayout />}>
              <Route index element={<HomePage />} />
            </Route>
          </Routes>
        </BrowserRouter>
      </>
  );
}

export default AppRoutes;
