import styled from "styled-components";

export const Sidebar = styled.div`
  background-color: rgb(40, 44, 52);
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  font-size: calc(10px + 2vmin);
  color: white;
  min-width: 200px;
  border-right: 4px solid goldenrod; // Add the golden border
`;

export const Description = styled.div`
  font-size: 14px;
  margin-bottom: 10px;
`;

export const Node = styled.div`
  height: 30px;
  padding: 8px;
  border: 1px solid rgb(26, 25, 43);
  border-radius: 4px;
  margin-bottom: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: grab;
  background-color: rgb(240, 240, 240);
  width: 80%;
  color: rgb(51, 51, 51);
`;
