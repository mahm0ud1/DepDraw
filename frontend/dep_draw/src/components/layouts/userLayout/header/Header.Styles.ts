import { Layout, Typography } from 'antd';
import styled from "styled-components";

const { Header: AntdHeader } = Layout;
const { Title } = Typography;

export const AntdHeaderStyle = styled(AntdHeader)`
    background-color: #282c34;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 20px;
    font-size: calc(10px + 2vmin);
    color: white;
    border-bottom: 4px solid goldenrod; /* Add the golden border at the bottom */
`;

export const TitleStyle = styled(Title)`
    color: white;
`