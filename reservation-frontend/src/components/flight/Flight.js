import StepNavigator from "../stepNavigator/StepNavigator";

export default function Flight({ destinationData }) {

  return (
    <>
      <StepNavigator step="flight" />
      Voo para {destinationData.name} ({destinationData.id})
    </>
  )
}