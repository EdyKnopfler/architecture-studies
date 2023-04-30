import StepNavigator from "../stepNavigator/StepNavigator";

export default function Flight({ destinationId }) {

  return (
    <>
      <StepNavigator step="flight" />
      Flight to {destinationId}
    </>
  )
}