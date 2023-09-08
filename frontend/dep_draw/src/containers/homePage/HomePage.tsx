import ToolsBar from "../../components/toolsBar/ToolsBar";
import { HomePageContainer } from "./Home.Styled";
import { ReactFlowProvider } from 'reactflow';

const HomePage = () => {
    return (
        <HomePageContainer>
            <ReactFlowProvider>
                Test
                <ToolsBar/>
                {/* <Sidebar />
                <Canvas onNodeSelected={handleNodeSelected} /> */}
            </ReactFlowProvider>
        </HomePageContainer>
    )
}

export default HomePage;